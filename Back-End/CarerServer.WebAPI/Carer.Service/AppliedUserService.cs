using Carer.Common;
using Carer.Model.DomainModels;
using Carer.Repository;
using Carer.Repository.Common;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Carer.Service
{
    class AppliedUserService : IAppliedUserService
    {
        private IAppliedUserRepository appliedUserRepository;
        private IAdRepository adRepository;

        public AppliedUserService(IAppliedUserRepository appliedUserRepository, IAdRepository adRepository)
        {
            this.appliedUserRepository = appliedUserRepository;
            this.adRepository = adRepository;
        }


        public async Task<List<AppliedUserDomainModel>> GetAllAppliedUserAsync(AppliedUserFilter filter)
        {
            return await appliedUserRepository.GetAllAppliedUsersAsync(filter);
        }

        public async Task PostAppliedUserAsync(AppliedUserDomainModel appliedUser)
        {
            await appliedUserRepository.PostAppliedUserAsync(appliedUser);
        }

        public async Task DeleteAppliedUserAsync(Guid userId, Guid adId)
        {
            await appliedUserRepository.DeleteAppliedUserAsync(userId, adId);
        }

        public async Task PutAppliedUserAsync(Guid userId, Guid adId, string status)
        {
            await appliedUserRepository.PutAppliedUserAsync(userId, adId,status);
            await adRepository.SetActiveAdAsync(adId);


        }

    }
}
