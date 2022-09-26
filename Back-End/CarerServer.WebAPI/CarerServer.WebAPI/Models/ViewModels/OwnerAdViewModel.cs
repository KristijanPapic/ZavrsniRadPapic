using Carer.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CarerServer.WebAPI.Models.ViewModels
{
    public class OwnerAdViewModel
    {
        private Guid id;
        private int type;
        private string job;
        private string description;
        private Guid petId;
        private string petName;
        private string petType;
        private Guid userId;
        private string userPhone;
        private string userEmail;
        private string userName;
        private string pictureURL;
        private string userRating;
        private int active;
        private int appliedUserCount;
        private string status;
        private DateTime dateCreated;
        private bool currentUserApplied = false;
        private SimpleAppliedUser appliedUser = null;

        public Guid Id { get => id; set => id = value; }
        public int Type { get => type; set => type = value; }
        public string Job { get => job; set => job = value; }
        public string Description { get => description; set => description = value; }
        public Guid PetId { get => petId; set => petId = value; }
        public Guid UserId { get => userId; set => userId = value; }
        public int Active { get => active; set => active = value; }
        public DateTime DateCreated { get => dateCreated; set => dateCreated = value; }
        public string PetName { get => petName; set => petName = value; }
        public string PetType { get => petType; set => petType = value; }
        public string UserPhone { get => userPhone; set => userPhone = value; }
        public string UserEmail { get => userEmail; set => userEmail = value; }
        public string UserName { get => userName; set => userName = value; }
        public string UserRating { get => userRating; set => userRating = value; }
        public string PictureURL { get => pictureURL; set => pictureURL = value; }
        public int AppliedUserCount { get => appliedUserCount; set => appliedUserCount = value; }
        public string Status { get => status; set => status = value; }
        public bool CurrentUserApplied { get => currentUserApplied; set => currentUserApplied = value; }
        public SimpleAppliedUser AppliedUser { get => appliedUser; set => appliedUser = value; }
    }
}
